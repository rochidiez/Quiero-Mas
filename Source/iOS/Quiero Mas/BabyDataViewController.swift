//
//  BabyDataViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/13/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import Firebase

class BabyDataViewController: UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    var name: String?
    var birthday: String?
    var email: String?
    
    @IBOutlet weak var babyNameTF: UITextField!
    @IBOutlet weak var babyNickNameTF: UITextField!
    @IBOutlet weak var babyBirthdayTF: UITextField!
    @IBOutlet weak var babyScroll: UIScrollView!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var babyImgView: UIImageView!

    override func viewDidLoad() {
        super.viewDidLoad()

        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureHandler))
        self.view.addGestureRecognizer(tapGesture)
        
        setBabyBirthdayTF()
    }
    
    func setBabyBirthdayTF() {
        babyBirthdayTF.inputView = datePicker
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
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        FirebaseAPI.storeFirebaseUser(firebaseID: firebaseID,
                                      name: name!,
                                      birthday: birthday!,
                                      email: email!,
                                      babyName: babyNameTF.text!,
                                      babyNickName: babyNickNameTF.text!,
                                      babyBirthday: babyBirthdayTF.text!)
        showMainVC()
    }
    
    @IBAction func datePickerValueChanged(_ sender: Any) {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let selectedDate = dateFormatter.string(from: datePicker.date)
        
        babyBirthdayTF.text = selectedDate
    }
    
    @IBAction func takePicture(_ sender: Any) {
        let imagePickerController = UIImagePickerController()
        imagePickerController.delegate = self
        
        let actionSheet = UIAlertController(title: "Photo Source", message: "Choose a source", preferredStyle: .actionSheet)
        
        actionSheet.addAction(UIAlertAction(title: "Camera", style: .default, handler: {(action: UIAlertAction) in
            imagePickerController.sourceType = .camera
            self.present(imagePickerController, animated: true, completion: nil)
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Photo Library", style: .default, handler: {(action: UIAlertAction) in
            imagePickerController.sourceType = .photoLibrary
            self.present(imagePickerController, animated: true, completion: nil)
        }))
        
        actionSheet.addAction(UIAlertAction(title: "Cancel", style: .default, handler: nil))
        
        present(actionSheet, animated: true, completion: nil)
        
        
    }
    
    //MARK: - Picker Controller Delegate
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        let image = info[UIImagePickerControllerOriginalImage] as! UIImage
        babyImgView.image = image
        picker.dismiss(animated: true, completion: nil)
        
        let imgNSURL = info[UIImagePickerControllerReferenceURL] as! NSURL
        let imgURL = URL(string: imgNSURL.path!)
        let user = FIRAuth.auth()?.currentUser
        guard let firebaseID = user?.uid else {return}
        FirebaseAPI.uploadBabyImg(url: imgURL!, firebaseID: firebaseID)
        
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        picker.dismiss(animated: true, completion: nil)
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
