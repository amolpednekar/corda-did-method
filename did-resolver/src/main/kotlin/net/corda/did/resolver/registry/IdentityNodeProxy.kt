package net.corda.did.resolver.registry

import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Uri
import java.io.InputStream
import java.net.URI

class IdentityNodeProxy(
		private val handler: (Request) -> Response,
		private val registry: IdentityNodeRegistry
) {

	/**
	 * TODO Consider whether this should return a strongly typed result instead.
	 *
	 * For now, this can be a proxy that returns a format-agnostic result.
	 */
	fun resolve(id: URI): InputStream {
		registry.location().let { location ->
			val port = location.port ?: 80

			val response = handler(Request(
					GET,
					Uri.of("https://${location.host}:$port/1.0/identifiers/$id")
			))

			if (!response.status.successful)
				throw SourceException("Target server responded with ${response.status}")

			return response.body.stream
		}
	}
}

class SourceException(s: String) : Exception(s)