//
//  MenuNavigationController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/6/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class MenuNavigationController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationBar.shadowImage = UIImage()
        self.navigationBar.titleTextAttributes = [NSForegroundColorAttributeName: UIColor.white, NSFontAttributeName : UIFont(name: "Cera-Bold", size: 18)!]
        self.navigationBar.topItem?.rightBarButtonItem?.setTitleTextAttributes([NSFontAttributeName : UIFont(name: "Cera-Regular", size: 16)!], for: .normal)
    }

}
