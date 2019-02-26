package io.ktor.mustache

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*


/**
 * Respond with the specified [template] passing [model]
 *
 * @see MustacheContent
 */
suspend fun ApplicationCall.respondTemplate(
    template: String,
    model: Any? = null,
    etag: String? = null,
    contentType: ContentType = ContentType.Text.Html.withCharset(
        Charsets.UTF_8
    )
) = respond(MustacheContent(template, model, etag, contentType))
