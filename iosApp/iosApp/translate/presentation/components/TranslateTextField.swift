//
//  TranslateTextField.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 15/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import SwiftUI
import shared
import UniformTypeIdentifiers

struct TranslateTextField: View {
    @Binding var fromText: String
    let toText: String?
    let isTranslating: Bool
    let fromLanguage: UiLanguage
    let toLanguage: UiLanguage
    let onTranslateEvent: (TranslateEvent) -> Void
    
    var body: some View {
        if toText == nil || isTranslating {
            IdleTextField(
                fromText: $fromText,
                isTranslating: isTranslating,
                onTranslateEvent: onTranslateEvent
            )
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: /*@START_MENU_TOKEN@*/10/*@END_MENU_TOKEN@*/)
        } else {
            TranslatedTextField(
                fromText: fromText,
                toText: toText ?? "",
                fromLangauge: fromLanguage,
                toLangauge: toLanguage,
                onTranslateEvent: onTranslateEvent
            )
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .animation(.easeInOut, value: isTranslating)
            .shadow(radius: 4)
            .onTapGesture {
                onTranslateEvent(TranslateEvent.EditTranslation())
            }
        }
    }
}

private extension TranslateTextField {
   
    struct IdleTextField: View {
        @Binding var fromText: String
        var toText: String?
        var isTranslating: Bool
        var onTranslateEvent: (TranslateEvent) -> Void
        
        
        var body: some View {
            TextEditor(text: $fromText)
                .frame(
                    maxWidth: .infinity,
                    minHeight: 200,
                    alignment: .topLeading
                )
                .padding()
                .foregroundColor(Color.onSurface)
                .overlay(alignment: .bottomTrailing) {
                    ProgressButton(
                        text: "Translate",
                        isLoading: isTranslating,
                        onClick: {
                            onTranslateEvent(TranslateEvent.Translate())
                        }
                    )
                    .padding(.trailing)
                    .padding(.bottom)
                }
                .onAppear {
                    UITextView.appearance().backgroundColor = .clear
                }
        }
    }
}


struct TranslatedTextField: View {
    var fromText: String
    var toText: String
    var fromLangauge: UiLanguage
    var toLangauge: UiLanguage
    var onTranslateEvent: (TranslateEvent) -> Void
    
    private let tts = TextToSpeech()
    
    
    var body: some View {
        VStack (alignment: .leading, content: {
            LanguageDisplay(langauge: fromLangauge)
            Text(fromText)
                .foregroundColor(.onSurface)
                
            HStack {
                Spacer()
                Button(action: {
                    UIPasteboard.general.setValue(
                        fromText,
                        forPasteboardType: UTType.plainText.identifier
                    )
                }, label: {
                    Image(uiImage: UIImage(named: "copy")!)
                        .renderingMode(.template)
                        .foregroundColor(.lightBlue)
                    
                })
                Button(action: {
                    onTranslateEvent(TranslateEvent.CloseTranslation())
                }, label: {
                    Image(systemName: "xmark")
                        .foregroundColor(.lightBlue)
                    
                })
            }
            Divider()
                .padding()
            LanguageDisplay(langauge: toLangauge)
            Text(toText)
                .foregroundColor(.onSurface)
            HStack {
                Spacer()
                Button(action: {
                    UIPasteboard.general.setValue(
                        toText,
                        forPasteboardType: UTType.plainText.identifier
                    )
                }, label: {
                    Image(uiImage: UIImage(named: "copy")!)
                        .renderingMode(.template)
                        .foregroundColor(.lightBlue)
                    
                })
                Button(action: {
                    tts.speak(
                        text: toText,
                        language: toLangauge.language.langCode
                    )
                }, label: {
                    Image(systemName: "speaker.wave.2")
                        .foregroundColor(.lightBlue)
                })
            }
        })
    }
}

#Preview {
    TranslateTextField(
        fromText: Binding(get: {"fromText"}, set: {_ in }),
        toText: nil,
        isTranslating: false,
        fromLanguage: UiLanguage(language: .english, imageName: "English"), 
        toLanguage: UiLanguage(language: .german, imageName: "German"),
        onTranslateEvent: {_ in }
    )
}

