// swift-tools-version:4.0
//
//  Package.swift
//  Ibpnh
//
//  Created by Axel Collard Bovy on 7/5/18.
//
//
// swift-tools-version:4.0
import PackageDescription

let package = Package(
    name: "Ibpnh",
    products: [
        .executable(name: "Run", targets: ["Run"]),
        .library(name: "IbpnhAPI", targets: ["IbpnhAPI"]),
        .library(name: "IbpnhCore", targets: ["IbpnhCore"]),
    ],
    dependencies: [
        // 💧 A server-side Swift web framework.
        .package(url: "https://github.com/vapor/vapor.git", from: "3.0.0"),

        // 🔵 Swift ORM (queries, models, relations, etc) built on PostgreSQL.
        .package(url: "https://github.com/vapor/fluent-postgresql.git", .upToNextMajor(from: "1.0.0-rc")),
        
//        .package(url: "https://github.com/vapor/routing.git", .upToNextMajor(from: "3.0.1")),
//        .package(url: "https://github.com/vapor/auth.git", .upToNextMajor(from: "2.0.0-rc")),
//        .package(url: "https://github.com/vapor/validation.git", .upToNextMajor(from: "2.0.0")),
//        .package(url: "https://github.com/vapor/jwt.git", .upToNextMajor(from: "3.0.0-rc")),

    ],
    targets: [
        .target(name: "Run", dependencies: ["IbpnhCore", "IbpnhAPI"]),
        .target(name: "IbpnhAPI", dependencies: ["IbpnhCore"], exclude: ["Public"]),
        .target(name: "IbpnhCore", dependencies: ["Vapor", "FluentPostgreSQL"], exclude: ["Public"]),
        .testTarget(name: "IbpnhApiTests", dependencies: ["IbpnhAPI"]),
        .testTarget(name: "IbpnhCoreTests", dependencies: ["IbpnhCore"])
    ]

)

