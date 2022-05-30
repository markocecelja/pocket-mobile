package com.mcecelja.pocket.enums

import com.mcecelja.pocket.utils.handlers.ErrorHandler
import com.mcecelja.pocket.utils.handlers.SessionExpiredErrorHandler

enum class ErrorEnum(val message: String, val errorHandler: ErrorHandler?) {

    UNRECOGNIZED_EXCEPTION("Nešto nije u redu, pokušajte ponovno kasnije!", null),
    SESSION_EXPIRED("Potrebna ponovna prijava!", SessionExpiredErrorHandler()),
    JSON_PARSE_ERROR("Nešto nije u redu, pokušajte ponovno kasnije!", null),
    BAD_REQUEST("Nešto nije u redu, pokušajte ponovno kasnije!", null),
    BAD_CREDENTIALS("Netočno korisničko ime ili lozinka!", null),
    UNAUTHORIZED("Nije dozvoljeno!", null),
    NON_EXISTING_ORGANIZATION("Organizacija ne postoji!", null),
    NON_EXISTING_CATEGORY("Kategorija ne postoji!", null),
    NON_EXISTING_POST("Objava ne postoji!", null),
    NON_EXISTING_ORGANIZATION_ROLE("Uloga organizacije ne postoji!", null),
    NON_EXISTING_CHAT("Traženi razgovor ne postoji!", null),
    PASSWORD_MISMATCH("Lozinke se ne podudaraju!", null),
    USERNAME_ALREADY_IN_USE("Korisničko ime se već koristi!", null)
}