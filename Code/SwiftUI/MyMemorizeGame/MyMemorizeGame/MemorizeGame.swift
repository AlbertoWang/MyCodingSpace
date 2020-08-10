//
//  MemorizeGame.swift
//  MyMemorizeGame
//
//  Created by Alberto Wang on 2020/8/6.
//  Copyright © 2020 Alberto Wang. All rights reserved.
//

// Model

import Foundation

struct MemorizeGame<CardContent> {
    // 卡片相关属性
    struct Card: Identifiable {
        var id: Int
        var isFaceUp: Bool = false
        var isMatched: Bool = false
        var content: CardContent
    }
    // 游戏所需卡片集合
    var cards: Array<Card>
    // 选择卡片动作
    // 允许修改 self 的 mutating 关键字
    mutating func choose(card: Card) {
        // print("chosen \(card)")
        let chosenIndex: Int = self.index(of: card)
        self.cards[chosenIndex].isFaceUp = !self.cards[chosenIndex].isFaceUp
    }
    // of 为外部调用时参数，card 为内部调用时参数
    func index(of card: Card) -> Int {
        for index in 0..<self.cards.count {
            if self.cards[index].id == card.id {
                return index
            }
        }
        return 0 // TODO: return null?
    }
    
    // 构造函数
    init(numberOfPairsOfCards: Int, cardContentFactory: (Int) -> CardContent) {
        cards = Array<Card>()
        for pairIndex in 0..<numberOfPairsOfCards {
            let content = cardContentFactory(pairIndex)
            cards.append(Card(id: pairIndex * 2, content: content))
            cards.append(Card(id: pairIndex * 2 + 1, content: content))
        }
    }
}
