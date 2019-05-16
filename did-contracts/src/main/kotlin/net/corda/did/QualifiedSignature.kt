package net.corda.did

import java.net.URI

/**
 * This encapsulates the fields used in the instruction JSON used in the DID envelope. This is inspired by
 * https://w3c-ccg.github.io/did-spec/#public-keys
 */
class QualifiedSignature(
		val suite: CryptoSuite,
		val target: URI,
		val value: ByteArray
)
