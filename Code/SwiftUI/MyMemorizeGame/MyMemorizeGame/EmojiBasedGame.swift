//
//  EmojiBasedGame.swift
//  MyMemorizeGame
//
//  Created by Alberto Wang on 2020/8/6.
//  Copyright © 2020 Alberto Wang. All rights reserved.
//

// ViewModel

import SwiftUI

// ObservedObject 将 ViewModel 的改变设置为可见
class EmojiBasedGame: ObservableObject {
    // @Published 装饰器监测后面变量的变化
    @Published private var memorizeGame: MemorizeGame<String> = EmojiBasedGame.createMemorizeGame()
    
    static func createMemorizeGame() -> MemorizeGame<String> {
        let emojis: Array<String> = ["🐶", "🍐", "🇨🇳", "😠", "☠️", "💺"]
        return MemorizeGame<String>(numberOfPairsOfCards: emojis.count) { emojisIndex in
            return emojis[emojisIndex]
        }
    }
    
    // MARK: - access to model
    var cards: Array<MemorizeGame<String>.Card> {
        memorizeGame.cards
    }
    
    // MARK: - intent
    func choose(card: MemorizeGame<String>.Card) {
        // 在 ViewModel 的 Model 改变之前，告诉 View 需要改变，如果使用了 @Published 装饰器，则不需要使用 objectWillChange.send() 方法
        // objectWillChange.send()
        memorizeGame.choose(card: card)
    }
}
