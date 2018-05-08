import Vapor
import IbpnhCore

/// Register your application's routes here.
public func routes(_ router: Router) throws {
    // Basic "Hello, world!" example
    router.get("hello") { req in
        return "Hello, world!"
    }

    // Example of configuring a controller
    let eventController = EventController()
    router.get("events", use: eventController.index)
    router.post("events", use: eventController.create)
    router.delete("events", Todo.parameter, use: eventController.delete)
}
