import SwiftUI
import shared

struct HomeScreen: View {
    
    @StateObject var viewModel = HomeViewModel()
    
    let gridColumns: [GridItem] = Array(repeating: GridItem(.flexible(), spacing: 12), count: 2)

	var body: some View {
        NavigationStack {
            ScrollView {
                LazyVGrid(columns: gridColumns, spacing: 12) {
                    
                    ForEach(viewModel.movies, id: \.id) { movie in
                        MovieGridItem(movie: movie)
                    }
                    
                }
            }
            .navigationTitle("Movies")
        }
        .task {
            await viewModel.loadMovies()
        }
	}
}

struct HomeScreen_Previews: PreviewProvider {
	static var previews: some View {
		HomeScreen()
	}
}
