package io.github.droidkaigi.confsched2023.model

public sealed class AppError : RuntimeException {
    private constructor(message: String?, cause: Throwable?) : super(message, cause)
    private constructor(cause: Throwable?) : super(cause)

    public sealed class ApiException(cause: Throwable?) : AppError(cause = cause) {
        public class NetworkException(cause: Throwable?) : ApiException(cause = cause)
        public class ServerException(cause: Throwable?) : ApiException(cause = cause)
        public class TimeoutException(cause: Throwable?) : ApiException(cause = cause)
        public class SessionNotFoundException(cause: Throwable?) : AppError(cause = cause)
        public class UnknownException(cause: Throwable?) : AppError(cause = cause)
    }

    public sealed class ExternalIntegrationError(cause: Throwable?) : AppError(cause = cause) {
        public class NoCalendarIntegrationFoundException(cause: Throwable?) :
            ExternalIntegrationError(cause)
    }

    public class UnknownException(cause: Throwable?) : AppError(cause = cause)
}
