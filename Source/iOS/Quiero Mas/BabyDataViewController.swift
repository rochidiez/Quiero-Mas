//
//  BabyDataViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/13/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class BabyDataViewController: UIViewController, UITextFieldDelegate {
    
    var name: String?
    var birthday: String?
    var email: String?
    
    @IBOutlet weak var babyNameTF: UITextField!
    @IBOutlet weak var babyNickNameTF: UITextField!
    @IBOutlet weak var babyBirthdayTF: UITextField!
    @IBOutlet weak var babyScroll: UIScrollView!

    override func viewDidLoad() {
        super.viewDidLoad()

        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureHandler))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    func tapGestureHandler() {
        let offSet = CGPoint(x:0, y:0)
        babyScroll.setContentOffset(offSet, animated: true)
        self.view.endEditing(true)
    }
    
    //MARK: - IBAction
    @IBAction func createAccount(_ sender: Any) {
        if babyNameTF.text == "" || babyNickNameTF.text == "" || babyBirthdayTF.text == "" {
            let alert = UIAlertController(title: "",
                                          message: "Faltan completar datos",
                                          preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK",
                                             style: .cancel, handler: nil)
            
            alert.addAction(cancelAction)
            present(alert, animated: true, completion: nil)
            return
        }
        FirebaseAPI.storeFirebaseWithBaby(name: name!, birthday: birthday!, email: email!, babyName: babyNameTF.text!, babyNickName: babyNickNameTF.text!, babyBirthday: babyBirthdayTF.text!)
        showMainVC()
    }
    
    //MARK: - Show VC
    func showMainVC() {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateInitialViewController()
        self.present(vc!, animated: false, completion: nil)
    }
    
    //MARK: - TextField Delegate
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        scrollToView(view: textField)
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        switch textField {
        case babyNameTF:
            babyNickNameTF.becomeFirstResponder()
        case babyNickNameTF:
            babyBirthdayTF.becomeFirstResponder()
        default:
            return true
        }
        return true
    }

    //MARK: - Scroll View Delegate
    func scrollToView(view: UIView) {
        var val:CGFloat = 0
        if DeviceType.IS_IPHONE_6P {
            val = 140
        } else if DeviceType.IS_IPHONE_6 {
            val = 200
        } else {
            val = 340
        }
        let offSet = CGPoint(x: 0, y: min(view.frame.origin.y + view.frame.size.height + 80, val))
        babyScroll.setContentOffset(offSet, animated: true)
    }
    
    //MARK: - Device
    enum UIUserInterfaceIdiom : Int
    {
        case Unspecified
        case Phone
        case Pad
    }
    
    struct ScreenSize
    {
        static let SCREEN_WIDTH         = UIScreen.main.bounds.size.width
        static let SCREEN_HEIGHT        = UIScreen.main.bounds.size.height
        static let SCREEN_MAX_LENGTH    = max(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
        static let SCREEN_MIN_LENGTH    = min(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT)
    }
    
    struct DeviceType
    {
        static let IS_IPHONE_4_OR_LESS  = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH < 568.0
        static let IS_IPHONE_5          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 568.0
        static let IS_IPHONE_6          = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 667.0
        static let IS_IPHONE_6P         = UIDevice.current.userInterfaceIdiom == .phone && ScreenSize.SCREEN_MAX_LENGTH == 736.0
        static let IS_IPAD              = UIDevice.current.userInterfaceIdiom == .pad && ScreenSize.SCREEN_MAX_LENGTH == 1024.0
    }

}
