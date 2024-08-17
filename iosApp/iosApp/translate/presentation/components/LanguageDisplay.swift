//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 15/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplay: View {
    var langauge: UiLanguage
    
    var body: some View {
        HStack {
            SmallLanguageIcon(langauge: langauge)
                .padding(.trailing, 5)
            Text(langauge.language.langName)
                .foregroundColor(.lightBlue)
        }
    }
}

#Preview {
    LanguageDisplay(
        langauge: UiLanguage(language: .english, imageName: "English")
    )
}
