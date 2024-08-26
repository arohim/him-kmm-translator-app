//
//  VoiceRecorderDisplay.swift
//  iosApp
//
//  Created by Abdulrohim 'Him' Sama on 23/8/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import SwiftUI

struct VoiceRecorderDisplay: View {
    var powerRatios: [Double]
    
    var body: some View {
        Canvas { context, size in
            context.clip(to: Path(CGRect(origin: .zero, size: size)))
            
            let barWidth = 3.0
            let barCount = Int(size.width / Double(2 * barWidth))
            let defaultLevel = 0.05
            let reversedRatios = powerRatios
                .map { ratio in
                    min(max(defaultLevel, ratio), 1.0)
                }
                .suffix(barCount)
                .reversed()
            for(i, powerRatio) in reversedRatios.enumerated() {
                let centerY = CGFloat(size.height / 2.0)
                let yToopStart = CGFloat(centerY - centerY * powerRatio)
                var path = Path()
                path.addRoundedRect(
                    in: CGRect(
                        x: CGFloat(size.width) - CGFloat(i) * 2.0 * barWidth,
                        y: yToopStart,
                        width: barWidth,
                        height: (centerY - yToopStart) * 2.0
                    ),
                    cornerSize: CGSize(width: 10.0, height: 10.0)
                )
            }
        }
        .gradientSurface()
        .cornerRadius(20)
        .padding(.horizontal, 15)
        .padding(.vertical, 15)
        .shadow(radius: 4)
        
    }
}

#Preview {
    VoiceRecorderDisplay(powerRatios: [])
}
