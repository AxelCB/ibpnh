//
//  User.swift
//  IbpnhCore
//
//  Created by Axel Collard on 7/5/18.
//
import Vapor
import FluentPostgreSQL

public final class User: PostgreSQLModel {
    // MARK: Properties
    
    /// The user's ID
    public var id: Int?
    
    /// The name of the user
    public var name: String
    
    /// The username of the user
    public var username: String
    
    /// The user's email
    public var email: String
    
    /// The user's _hashed_ password
    public var password: String
    
    /// The user's role
    public var roleId: Role.ID?
    
    ///Temporary roletype property for creation of role
    public var roleType: RoleTypeEnum?
    
    /// Creates a new User
    public init(name: String, username: String, email: String, password: String, role: Role) {
        self.name = name
        self.email = email
        self.username = username
        self.password = password
        self.roleId = role.id ?? nil
        /*
         do {
         self.roleType = try role.roleType?.get()?.type
         } catch {
         print("Trying to create user with invalid role-roletype!")
         }
         */
    }
    
    public init(name: String, username: String, email: String, password: String, roleType: RoleTypeEnum? = .User) {
        self.name = name
        self.email = email
        self.username = username
        self.password = password
        self.roleType = roleType
        self.roleId = nil
    }
}

// MARK: Relations
extension User {
    /// Fluent relation for accessing the role
    public var role: Parent<User, Role>? {
        return parent(\User.roleId)
    }
}

/// Allows `User` to be encoded to and decoded from HTTP messages.
extension User: Content {}
/// Allows `User` to be used as a dynamic parameter in route definitions.
extension User: Parameter {}
/// Allows `User` to be used as a dynamic migration.
extension User: Migration {}

// MARK: Fluent Database Hooks
extension User {
    public func willCreate(on connection: PostgreSQLConnection) throws -> Future<User> {
        if var roleTypeEnum = roleType, roleId == nil {
            return User.query(on: connection).count().flatMap(to: User.self) { total in
                if total == 0 {
                    roleTypeEnum = .Admin
                }
                return try RoleType.query(on: connection).filter(\RoleType.type == roleTypeEnum).first().flatMap(to: User.self) { roleType in
                    if let roleType = roleType {
                        return Role(roleType: roleType).create(on: connection).flatMap(to: User.self) { role in
                            self.roleId = role.id!
                            return Future.map(on: connection) { self }
                        }
                    }
                    return Future.map(on: connection) { self }
                }
            }
        } else {
            return Future.map(on: connection) { self }
        }
    }
    
    public func willDelete(on connection: PostgreSQLConnection) throws -> Future<User> {
        if let role = role {
            return try role.get(on: connection).delete(on: connection).flatMap(to: User.self) { role in
                return Future.map(on: connection) { self }
            }
        } else {
            return Future.map(on: connection) { self }
        }
    }
}
