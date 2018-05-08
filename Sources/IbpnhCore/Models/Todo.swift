import FluentPostgreSQL
import Vapor

/// A single entry of a Todo list.
public final class Todo: PostgreSQLModel {
    /// The unique identifier for this `Todo`.
    public var id: Int?

    /// A title describing what this `Todo` entails.
    public var title: String

    /// Creates a new `Todo`.
    public init(id: Int? = nil, title: String) {
        self.id = id
        self.title = title
    }
}

/// Allows `Todo` to be used as a dynamic migration.
extension Todo: Migration { }

/// Allows `Todo` to be encoded to and decoded from HTTP messages.
extension Todo: Content { }

/// Allows `Todo` to be used as a dynamic parameter in route definitions.
extension Todo: Parameter { }
