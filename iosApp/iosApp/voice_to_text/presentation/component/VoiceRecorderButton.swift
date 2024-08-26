//
//  VoiceRecorderButton.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 23/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import SwiftUI
import shared

struct VoiceRecorderButton: View {
    var displayState: DisplayState
    var onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            ZStack {
                Circle()
                    .foregroundColor(.primaryColor)
                    .padding()
                icon
                    .foregroundColor(.onPrimary)
            }
        }
        .frame(maxWidth: 100, maxHeight: 100)
    }
    
    var icon: some View {
        
        switch displayState {
        case .speaking:
            return Image(systemName: "stop.fill")
        case .displayingResults:
            return Image(systemName: "checkmark")
        default:
            return Image(uiImage: UIImage(named: "mic")!)
        }
    }
    
    
}

#Preview {
    VoiceRecorderButton(
        displayState: DisplayState.waitingToTalk,
        onClick: {}
    )
}
