//
//  Dictionary+toArray.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/23/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import Foundation

extension Dictionary {
    
    func toArray() -> [[String:Any]] {
        var arr = [[String:Any]]()
        var i = 0
        for (key, element) in self {
            arr.insert([key as! String:element], at: i)
            i += 1
        }
        return arr
    }
    
}
