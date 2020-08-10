//
//  ContentView.swift
//  MyMemorizeGame
//
//  Created by Alberto Wang on 2020/8/5.
//  Copyright © 2020 Alberto Wang. All rights reserved.
//

// View

import SwiftUI

// 整个游戏的 View
struct MyMemroizeGameView: View {
    // @ObservedObject 装饰器使得 View 可以根据 ViewModel 发布的改变信息而更新
    @ObservedObject var viewModel: EmojiBasedGame
    var body: some View {
        HStack {
            ForEach(viewModel.cards) {card in
                CardView(card: card).onTapGesture {
                    self.viewModel.choose(card: card)
                }
            }
        }.padding().foregroundColor(Color.blue).font(Font.largeTitle)
    }
}

// 定义一张 Card 的 View
struct CardView: View {
    // 定义一个 MemorizeGame 中的 Card
    var card: MemorizeGame<String>.Card
    // 根据 Card 的属性绘制 Card 的样式
    var body: some View {
        ZStack {
            if card.isFaceUp {
                RoundedRectangle(cornerRadius: 10).fill(Color.white)
                RoundedRectangle(cornerRadius: 10).stroke(lineWidth: 3)
                Text(card.content)
            } else {
                RoundedRectangle(cornerRadius: 10).fill()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        MyMemroizeGameView(viewModel: EmojiBasedGame())
    }
}
