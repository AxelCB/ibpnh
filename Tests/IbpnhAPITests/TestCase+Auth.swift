//
//  TestCase+Auth.swift
//  IbpnhApiTests
//
//  Created by Axel Collard on 7/5/18.
//
import IbpnhCore
import FluentPostgreSQL
import XCTest


class TestCase: XCTestCase {
    static let testAdmin = User(name: "Test Admin", username: "testAdmin", email: "testadmin@vaporTest.com", password: "testAdmin", roleType: .Admin)
    static let testUser = User(name: "Test User", username: "testUser", email: "testuser@vaporTest.com", password: "testUser")
    var token: String?
    
    func initializeUsers(on connection: DatabaseConnection) throws {
        try User.query(on: connection).filter(\User.username == "TestCase.testAdmin.username").count().flatMap { adminCount in
            if adminCount == 0 {
                print("Admin initialization")
                return try TestCase.testAdmin.save(on: connection).wait()
            }
        }
        try User.query(on: connection).filter(\User.username == "TestCase.testUser.username").count().flatMap { adminCount in
            if adminCount == 0 {
                print("Admin initialization")
                return try TestCase.testUser.save(on: connection).wait()
            }
        }
    }
}
