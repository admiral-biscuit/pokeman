package de.admiralbiscuit.akquinet.pokeman.errors

// This is the interface that all errors should implement.
// For simplicity, we just assume a "message" field.

interface GeneralError {
  val message: String
}
