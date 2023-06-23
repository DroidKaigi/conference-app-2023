//
//  ContentView.swift
//  DroidKaigi2023
//
//  Created by RyuNen344 on 2023/04/27.
//

import shared
import SwiftUI

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let vc = DarwinContributorsKt.viewController()
        vc.overrideUserInterfaceStyle = .light
        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("Hello, world!")
            Text(EntryPoint().echo())
            ComposeView()
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
