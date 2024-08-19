//
//  TextToSpeech.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 17/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import Foundation
import AVFoundation

struct TextToSpeech {
    
    private let synthesizer = AVSpeechSynthesizer()
    
    func speak(text: String, language: String) {
        let utterance = AVSpeechUtterance(string: text)
        utterance.voice = AVSpeechSynthesisVoice(language: language)
        utterance.volume = 1
        synthesizer.speak(utterance)
    }
}
