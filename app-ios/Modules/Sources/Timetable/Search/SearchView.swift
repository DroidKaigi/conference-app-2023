import Assets
import Model
import shared
import SwiftUI
import Theme

enum SearchRouting: Hashable {
    case session(TimetableItem)
}

struct SearchView<SessionView: View>: View {
    @ObservedObject var viewModel: SearchViewModel = .init()
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
                    ScrollView {
                        VStack {
//                            ScrollView(.horizontal) {
//                                HStack {
//                                    FilterLabel(
//                                        title: "開催日",
//                                        selections: state.days,
//                                        isSelected: !viewModel.state.filters.days.isEmpty,
//                                        onSelect: { day in
//                                            viewModel.filter {
//                                                $0.doCopy(
//                                                    days: [day],
//                                                    categories: $0.categories,
//                                                    sessionTypes: $0.sessionTypes,
//                                                    languages: $0.languages,
//                                                    filterFavorite: $0.filterFavorite,
//                                                    searchWord: $0.searchWord
//                                                )
//                                            }
//                                        }
//                                    )
//                                }
//                            }
                            TimetableListView(
                                timetableTimeGroupItems: state.timeGroupTimetableItems
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
            prompt: "気になる技術を入力"
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
            Text("「\(inputText)」と一致する検索結果がありません")
                .font(Font.system(size: 16, weight: .medium))
                .multilineTextAlignment(.center)
                .foregroundStyle(AssetColors.Surface.onSurfaceVariant.swiftUIColor)
        }
        .frame(minWidth: .infinity, minHeight: .infinity)
    }
}

struct FilterLabel<Selection: RandomAccessCollection>: View{
    let title: String
    let selections: Selection
    let isSelected: Bool
    let onSelect: (Selection.Element) -> Void

    var body: some View {
        Menu {
            ForEach(selections) { selection in
                Button {
                    onSelect(selection)
                } label: {
                    Text(selection.description)
                }
            }
        } label: {
            HStack {
                if isSelected {
                    Assets.Icons.check.swiftUIImage
                }
                Text(title)
                Assets.Icons.arrowDropDown.swiftUIImage
            }
            .padding(.horizontal, 8)
            .padding(.vertical, 6)
            .background {
                if isSelected {
                    AssetColors.Secondary.secondaryContainer.swiftUIColor
                } else {
                    AssetColors.Surface.surface.swiftUIColor
                }
            }
            .foregroundStyle(
                isSelected
                    ? AssetColors.Secondary.onSecondaryContainer.swiftUIColor
                    : AssetColors.Surface.onSurface.swiftUIColor
            )
            .clipShape(RoundedRectangle(cornerRadius: 4))
            .overlay {
                if !isSelected {
                    RoundedRectangle(cornerRadius: 4)
                        .inset(by: 0.5)
                        .stroke(
                            AssetColors.outline.swiftUIColor,
                            lineWidth: 1
                        )
                } else {
                    EmptyView()
                }
            }
        }
    }
}

#Preview {
    SearchView(
        sessionViewBuilder: {_ in EmptyView()}
    )
}
