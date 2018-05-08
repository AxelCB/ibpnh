//
//  Token.swift
//  IbpnhCore
//
//  Created by Axel Collard on 7/5/18.
//
import Vapor
import FluentPostgreSQL

public final class Token: PostgreSQLModel{
    /// Token ID
    public var id: Int?
    
    /// The actual token
    public var token: String
    
    /// The identifier of the user to which the token belongs
    public var userId: Int
    
    /// Creates a new Token
    public init(string: String, user: User) {
        token = string
        userId = user.id!
    }
}

// MARK: Relations
extension Token {
    /// Fluent relation for accessing the user
    var user: Parent<Token, User>? {
        return parent(\Token.userId)
    }
}

/// Allows `Token` to be encoded to and decoded from HTTP messages.
extension Token: Content {}
/// Allows `Token` to be used as a dynamic parameter in route definitions.
extension Token: Parameter {}
/// Allows `Token` to be used as a dynamic migration.
extension Token: Migration {}
