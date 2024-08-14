//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 14/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selectLanguage: (UiLanguage) -> Void
    
    var body: some View {
        Menu (
            content: {
                VStack{
                    ForEach(UiLanguage.Companion().allLanguages, id: \.language.langCode) { language in
                        LanguageDropDownItem(language: language) {
                            selectLanguage(language)
                        }
                    }
                }
            },
            label: {
                HStack {
                    SmallLanguageIcon(langauge: language)
                    Text(language.language.langName)
                        .foregroundColor(.lightBlue)
                    Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                        .foregroundColor(.lightBlue)
                }
            }
        )

    }
}

#Preview {
    LanguageDropDown(
        language: UiLanguage(language:.german, imageName: "german"),
        isOpen: true,
        selectLanguage: { uiLanguage in
            
        }
    )
}
