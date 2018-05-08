//
//  Role.swift
//  IbpnhCore
//
//  Created by Axel Collard on 7/5/18.
//
import Vapor
import FluentPostgreSQL

public final class Role: PostgreSQLModel {
    
    // MARK: Properties and database keys
    
    public var id: Int?
    
    /// The name of the role type
    public var roleTypeId: RoleType.ID?
    
    /// Creates a new Role
    public init(roleType: RoleType) {
        self.roleTypeId = roleType.id
    }
}


// MARK: Relations
extension Role {
    /// Fluent relation for accessing the user
    public var roleType: Parent<Role, RoleType>? {
        return parent(\Role.roleTypeId)
    }
}

/// Allows `Role` to be encoded to and decoded from HTTP messages.
extension Role: Content {}
/// Allows `Role` to be used as a dynamic parameter in route definitions.
extension Role: Parameter {}
/// Allows `Role` to be used as a dynamic migration.
extension Role: Migration {}
