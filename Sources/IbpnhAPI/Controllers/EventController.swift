import Vapor
import IbpnhCore

/// Controls basic CRUD operations on `Event`s.
final class EventController {
    /// Returns a list of all `Event`s.
    func index(_ req: Request) throws -> Future<[Event]> {
        return Event.query(on: req).all()
    }

    /// Saves a decoded `Event` to the database.
    func create(_ req: Request) throws -> Future<Event> {
        return try req.content.decode(Event.self).flatMap { event in
            return event.save(on: req)
        }
    }

    /// Deletes a parameterized `Event`.
    func delete(_ req: Request) throws -> Future<HTTPStatus> {
        return try req.parameters.next(Event.self).flatMap { event in
            return event.delete(on: req)
        }.transform(to: .ok)
    }
}
