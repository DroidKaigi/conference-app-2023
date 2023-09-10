import Lottie
import SwiftUI

public typealias LottieAnimation = Lottie.LottieAnimation

public struct LottieView: UIViewRepresentable {
    private let animation: LottieAnimation
    private let loopMode: LottieLoopMode
    private let completionHandler: LottieCompletionBlock

    public init(animation: LottieAnimation, loopMode: LottieLoopMode, completionHandler: @escaping LottieCompletionBlock) {
        self.animation = animation
        self.loopMode = loopMode
        self.completionHandler = completionHandler
    }

    public func makeUIView(context: Context) -> UIView {
        let view = UIView(frame: .zero)
        let animationView = LottieAnimationView(animation: animation)
        animationView.contentMode = .scaleAspectFit
        animationView.loopMode = loopMode
        animationView.play(completion: completionHandler)

        view.addSubview(animationView)

        animationView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            animationView.heightAnchor.constraint(equalTo: view.heightAnchor),
            animationView.widthAnchor.constraint(equalTo: view.widthAnchor),
        ])

        return view
    }

    public func updateUIView(_ uiView: UIView, context: Context) {
    }
}

public extension LottieAnimation {
    func swiftUIAnimation(loopMode: LottieLoopMode, completionHandler: @escaping LottieCompletionBlock) -> some View {
        LottieView(
            animation: self,
            loopMode: loopMode,
            completionHandler: completionHandler
        )
    }
}
