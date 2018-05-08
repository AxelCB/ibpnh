//
//  RoleType.swift
//  IbpnhCore
//
//  Created by Axel Collard on 7/5/18.
//
import Vapor
import FluentPostgreSQL

public final class RoleType: PostgreSQLModel {
    
    /// The unique identifier for this roletype.
    public var id: Int?
    
    /// The name of the roletype
    public var name: String
    
    /// The type of the roletype
    public var type: RoleTypeEnum
    
    /// Creates a new RoleType
    init(id: Int? = nil, name: String, type: RoleTypeEnum) {
        self.name = name
        self.type = type
        self.id = id
    }
}

/// Allows `RoleType` to be encoded to and decoded from HTTP messages.
extension RoleType: Content {}
/// Allows `RoleType` to be used as a dynamic parameter in route definitions.
extension RoleType: Parameter {}
/// Allows `RoleType` to be used as a dynamic migration.
extension RoleType: Migration {
    /// Prepares a table/collection in the database
    /// for storing RoleTypes
    public static func prepare(on connection: PostgreSQLConnection) -> Future<Void> {
        return Database.create(self, on: connection) { builder in
            try addProperties(to: builder)
            try builder.addIndex(to: \.type, isUnique: true)
        }.do {
            for roleType in RoleTypeEnum.allValues() {
                RoleType(name: roleType.name, type: roleType).create(on: connection).catch{ error in
                    print("Couldn't create RoleType \(roleType.rawValue) with error: \(error.localizedDescription)")
                }
            }
        }
    }
}

public enum RoleTypeEnum: Int, Content, ReflectionDecodable, PostgreSQLColumnStaticRepresentable {
    public static var postgreSQLColumn = PostgreSQLColumn(type: .int8)
    
    case Admin
    case User
    
    public static func reflectDecoded() throws -> (RoleTypeEnum, RoleTypeEnum) {
        return (.Admin,.User)
    }
    
    var name: String {
        get {
            switch self {
            case .Admin:
                return "Admin"
            case .User:
                return "User"
            }
        }
    }
    
    static func allValues() -> [RoleTypeEnum] {
        return [.Admin, .User]
    }
}
