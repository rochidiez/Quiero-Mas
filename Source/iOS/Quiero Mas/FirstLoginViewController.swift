//
//  FirstLoginViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 6/2/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import FBSDKLoginKit
import FBSDKCoreKit
import Firebase

class FirstLoginViewController: UIViewController {
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        autoLogin()
        FirebaseAPI.getInitialData()
    }

    func autoLogin() {
        let user = FIRAuth.auth()?.currentUser
        if (user != nil) {
            showMainVC()
        } else {
            performSegue(withIdentifier: "loginSegue", sender: nil)
        }
    }
    
    func showMainVC() {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateInitialViewController()
        self.present(vc!, animated: false, completion: nil)
    }

}
