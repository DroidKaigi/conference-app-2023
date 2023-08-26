import Dependencies
import shared

public struct StaffsDataProvider {
    private static var staffRepository: StaffRepository {
        Container.shared.get(type: StaffRepository.self)
    }

    public let refresh: () async throws -> Void
    public let staffs: () -> AsyncThrowingStream<[Staff], Error>
}

extension StaffsDataProvider: DependencyKey {
    @MainActor
    public static var liveValue: StaffsDataProvider = StaffsDataProvider(
        refresh: { @MainActor in
            try await staffRepository.refresh()
        },
        staffs: {
            staffRepository.staffs().stream()
        }
    )

    public static var testValue: StaffsDataProvider = StaffsDataProvider(
        refresh: {},
        staffs: {
            .init {
                Staff.companion.fakes()
            }
        }
    )
}

 public extension DependencyValues {
    var staffsData: StaffsDataProvider {
        get { self[StaffsDataProvider.self] }
        set { self[StaffsDataProvider.self] = newValue }
    }
}
