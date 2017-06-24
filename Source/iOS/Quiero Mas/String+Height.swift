//
//  String+Height.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/23/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import Foundation

extension String {
    func height(withConstrainedWidth width: CGFloat, font: UIFont) -> CGFloat {
        let constraintRect = CGSize(width: width, height: .greatestFiniteMagnitude)
        let boundingBox = self.boundingRect(with: constraintRect, options: .usesLineFragmentOrigin, attributes: [NSFontAttributeName: font], context: nil)
        
        return boundingBox.height
    }
}
