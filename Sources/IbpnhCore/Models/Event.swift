//
//  Event.swift
//  IbpnhCore
//
//  Created by Axel Collard on 7/5/18.
//
import Vapor
import FluentPostgreSQL

public final class Event: PostgreSQLModel {
    public var id: Int?
    
    /// The description of the event
    public var description: String
    
    /// The event's date
    public var date: Date
    
    /// Creates a new Event
    public init(id: Int? = nil, description: String, date: Date) {
        self.id = id
        self.description = description
        self.date = date
    }
}

// MARK: Fluent Preparation
extension Event: Migration {}
extension Event: Content {}
extension Event: Parameter {}
