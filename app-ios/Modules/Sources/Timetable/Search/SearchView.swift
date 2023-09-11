import Assets
import Model
import shared
import SwiftUI
import Theme

enum SearchRouting: Hashable {
    case session(TimetableItem)
}

struct SearchView<SessionView: View>: View {
    @StateObject var viewModel: SearchViewModel = .init()
    private let sessionViewBuilder: ViewProvider<TimetableItem, SessionView>

    public init(sessionViewBuilder: @escaping ViewProvider<TimetableItem, SessionView>) {
        self.sessionViewBuilder = sessionViewBuilder
    }

    var body: some View {
        Group {
            switch viewModel.state.loadingState {
            case .initial, .loading:
                ProgressView()
                    .task {
                        await viewModel.load()
                    }
            case .loaded(let state):
                if state.timeGroupTimetableItems.isEmpty {
                    SearchEmptyView(
                        inputText: viewModel.state.filters.searchWord
                    )
                } else {
                    VStack {
                        ScrollView(.horizontal) {
                            HStack {
                                SearchFilterLabel(
                                    title: L10n.Search.day,
                                    selections: state.days,
                                    selectedSelection: viewModel.state.filters.days.first,
                                    onSelect: { day in
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: [day],
                                                categories: $0.categories,
                                                sessionTypes: $0.sessionTypes,
                                                languages: $0.languages,
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    },
                                    onDeselect: {
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: [],
                                                categories: $0.categories,
                                                sessionTypes: $0.sessionTypes,
                                                languages: $0.languages,
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    }
                                )
                                SearchFilterLabel(
                                    title: L10n.Search.category,
                                    selections: state.categories,
                                    selectedSelection: viewModel.state.filters.categories.first,
                                    onSelect: { category in
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: $0.days,
                                                categories: [category],
                                                sessionTypes: $0.sessionTypes,
                                                languages: $0.languages,
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    },
                                    onDeselect: {
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: $0.days,
                                                categories: [],
                                                sessionTypes: $0.sessionTypes,
                                                languages: $0.languages,
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    }
                                )
                                SearchFilterLabel(
                                    title: L10n.Search.sessionType,
                                    selections: state.sessionTypes,
                                    selectedSelection: viewModel.state.filters.sessionTypes.first,
                                    onSelect: { sessionType in
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: $0.days,
                                                categories: $0.categories,
                                                sessionTypes: [sessionType],
                                                languages: $0.languages,
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    },
                                    onDeselect: {
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: $0.days,
                                                categories: $0.categories,
                                                sessionTypes: [],
                                                languages: $0.languages,
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    }
                                )
                                SearchFilterLabel(
                                    title: L10n.Search.supportedLanguages,
                                    selections: state.languages,
                                    selectedSelection: viewModel.state.filters.languages.first?.timetable,
                                    onSelect: { language in
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: $0.days,
                                                categories: $0.categories,
                                                sessionTypes: $0.sessionTypes,
                                                languages: [language.toLang()],
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    },
                                    onDeselect: {
                                        viewModel.filter {
                                            $0.doCopy(
                                                days: $0.days,
                                                categories: $0.categories,
                                                sessionTypes: $0.sessionTypes,
                                                languages: [],
                                                filterFavorite: $0.filterFavorite,
                                                searchWord: $0.searchWord
                                            )
                                        }
                                    }
                                )
                            }
                            .padding(.horizontal, 16)
                        }
                        ScrollView {
                            TimetableListView(
                                timetableTimeGroupItems: state.timeGroupTimetableItems,
                                searchWord: viewModel.state.filters.searchWord,
                                onToggleBookmark: { id in
                                    viewModel.toggleBookmark(id)
                                }
                            )
                        }
                        .padding(.horizontal, 16)
                    }
                }
            case .failed:
                EmptyView()
            }
        }
        .background(AssetColors.Surface.surface.swiftUIColor)
        .searchable(
            text: Binding(
                get: {
                    viewModel.state.filters.searchWord
                },
                set: { text in
                    viewModel.filter {
                        $0.doCopy(
                            days: $0.days,
                            categories: $0.categories,
                            sessionTypes: $0.sessionTypes,
                            languages: $0.languages,
                            filterFavorite: $0.filterFavorite,
                            searchWord: text
                        )
                    }
                }
            ),
            placement: .navigationBarDrawer(displayMode: .always),
            prompt: L10n.Search.searchPlaceholder
        )
    }
}

private struct SearchEmptyView: View {
    let inputText: String

    var body: some View {
        VStack(spacing: 20) {
            Assets.Images.onsenBucket.swiftUIImage
                .resizable()
                .frame(width: 36, height: 36)
                .padding(24)
                .background(AssetColors.Secondary.secondaryContainer.swiftUIColor)
                .clipShape(RoundedRectangle(cornerRadius: 24))
            Text(L10n.Search.searchResultNotFound(inputText))
                .textStyle(TypographyTokens.titleMedium)
                .multilineTextAlignment(.center)
                .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

struct SearchFilterLabel<T: TimetableFilter>: View {
    let title: String
    let selections: [T]
    let selectedSelection: T?
    let onSelect: (T) -> Void
    let onDeselect: () -> Void

    var isSelected: Bool {
        selectedSelection != nil
    }

    var body: some View {
        Menu {
            ForEach(selections, id: \.id) { selection in
                Button {
                    if selection == selectedSelection {
                        onDeselect()
                    } else {
                        onSelect(selection)
                    }
                } label: {
                    Text(selection.title.currentLangTitle)
                }
            }
        } label: {
            FilterLabel(
                title: isSelected ? selectedSelection!.title.currentLangTitle : title,
                isSelection: true,
                isSelected: isSelected,
                onSelect: nil,
                onDeselect: nil
            )
        }
    }
}

#Preview {
    SearchView(
        sessionViewBuilder: {_ in EmptyView()}
    )
}
