//
//  SmallLanguageIcon.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 14/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmallLanguageIcon: View {
    var langauge: UiLanguage
    
    var body: some View {
        Image(uiImage: UIImage(named:langauge.imageName.lowercased())!)
        .resizable()
        .frame(width: 30, height: 30)
    }
}

#Preview {
    SmallLanguageIcon(
        langauge: UiLanguage(language: .english, imageName: "English")
    )
}
