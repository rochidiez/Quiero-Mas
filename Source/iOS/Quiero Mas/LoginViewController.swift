//
//  LoginViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/21/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import AVFoundation
import FBSDKLoginKit
import Firebase
import CoreData

class LoginViewController: UIViewController, UITextFieldDelegate, FBSDKLoginButtonDelegate {
    
    var avPlayer: AVPlayer!
    var avPlayerLayer: AVPlayerLayer!
    var paused: Bool = false
    
    @IBOutlet weak var loginView: UIView!
    
    @IBOutlet weak var emailView: UIView!
    @IBOutlet weak var loginEmailTF: UITextField!
    @IBOutlet weak var loginPassTF: UITextField!
    
    @IBOutlet weak var forgotView: UIView!
    
    
    @IBOutlet weak var registerView: UIView!
    @IBOutlet weak var registerNameTF: UITextField!
    @IBOutlet weak var registerDateTF: UITextField!
    @IBOutlet weak var registerEmailTF: UITextField!
    @IBOutlet weak var registerPassTF: UITextField!
    @IBOutlet weak var registerConfirmPassTF: UITextField!
    
    @IBOutlet weak var loginScroll: UIScrollView!
    @IBOutlet weak var dasanTopConstraint: NSLayoutConstraint!
    @IBOutlet weak var emailNoAccountConstraint: NSLayoutConstraint!
    @IBOutlet weak var forgotPassNoAccountConstraint: NSLayoutConstraint!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        let theUrl = NSURL(fileURLWithPath: Bundle.main.path(forResource: "video-background_1200x720", ofType: "mp4")!)
        
        avPlayer = AVPlayer(url: theUrl as URL)
        
        avPlayerLayer = AVPlayerLayer(player: avPlayer)
        avPlayerLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
        avPlayer.actionAtItemEnd = .none
        
        avPlayerLayer.frame = view.layer.frame
        
        view.layer.insertSublayer(avPlayerLayer, at: 0)
        
        NotificationCenter.default.addObserver(self,
        selector: #selector(playerItemDidReachEnd(notification:)),
        name: NSNotification.Name.AVPlayerItemDidPlayToEndTime,
        object: avPlayer.currentItem)
        
        adjustDasanConstraint()
        adjustEmailNoAccountConstraint()
        adjustForgotPasswordNoAccountConstraint()
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureHandler))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    func playerItemDidReachEnd(notification: Notification) {
        let p: AVPlayerItem = notification.object as! AVPlayerItem
        p.seek(to: kCMTimeZero)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        avPlayer.play()
        paused = false
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        avPlayer.pause()
        paused = true
    }
    
    func tapGestureHandler() {
        let offSet = CGPoint(x:loginScroll.contentSize.height - loginScroll.bounds.size.height, y:0)
        loginScroll.setContentOffset(offSet, animated: true)
        self.view.endEditing(true)
    }
    
    
    //MARK: - Constraints
    func adjustDasanConstraint() {
        if DeviceType.IS_IPHONE_6P {
            dasanTopConstraint.constant = 300
        }
        
        if DeviceType.IS_IPHONE_6 {
            dasanTopConstraint.constant = 230
        }
        
        if DeviceType.IS_IPHONE_5 {
            dasanTopConstraint.constant = 110
        }
    }
    
    func adjustEmailNoAccountConstraint() {
        if DeviceType.IS_IPHONE_6P {
            emailNoAccountConstraint.constant = 210
        }
        
        if DeviceType.IS_IPHONE_6 {
            emailNoAccountConstraint.constant = 140
        }
        
        if DeviceType.IS_IPHONE_5 {
            emailNoAccountConstraint.constant = 40
        }
    }
    
    func adjustForgotPasswordNoAccountConstraint() {
        if DeviceType.IS_IPHONE_6P {
            forgotPassNoAccountConstraint.constant = 260
        }
        
        if DeviceType.IS_IPHONE_6 {
            forgotPassNoAccountConstraint.constant = 190
        }
        
        if DeviceType.IS_IPHONE_5 {
            forgotPassNoAccountConstraint.constant = 90
        }
    }
    
    //MARK: - TextField Delegate
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        scrollToView(view: textField)
        
        return true
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        switch textField {
        case loginEmailTF:
            loginPassTF.becomeFirstResponder()
        case registerNameTF:
            registerDateTF.becomeFirstResponder()
        case registerDateTF:
            registerEmailTF.becomeFirstResponder()
        case registerEmailTF:
            registerPassTF.becomeFirstResponder()
        case registerPassTF:
            registerConfirmPassTF.becomeFirstResponder()
        default:
            return true
        }
        
        return true
    }
    
    //MARK: - Scroll View Delegate
    func scrollToView(view: UIView) {
        let offSet = CGPoint(x: 0, y: view.frame.origin.y + view.frame.height + 20)
        loginScroll.setContentOffset(offSet, animated: true)
    }
    
    //MARK: - Show View
    func showLoginView() {
        loginView.isHidden = false
        emailView.isHidden = true
        forgotView.isHidden = true
        registerView.isHidden = true
        loginScroll.isScrollEnabled = false
    }
    
    func showEmailView() {
        loginView.isHidden = true
        emailView.isHidden = false
        forgotView.isHidden = true
        registerView.isHidden = true
        loginScroll.isScrollEnabled = false
    }
    
    func showForgotView() {
        loginView.isHidden = true
        emailView.isHidden = true
        forgotView.isHidden = false
        registerView.isHidden = true
        loginScroll.isScrollEnabled = false
    }
    
    func showRegisterView() {
        loginView.isHidden = true
        emailView.isHidden = true
        forgotView.isHidden = true
        registerView.isHidden = false
        loginScroll.isScrollEnabled = true
    }
    
    
    //MARK: - IBAction
    @IBAction func showEmailAction(_ sender: Any) {
        showEmailView()
    }
    
    @IBAction func forgotPasswordAction(_ sender: Any) {
        showForgotView()
    }
    
    @IBAction func registerAction(_ sender: Any) {
        showRegisterView()
    }
    
    @IBAction func loginAction(_ sender: Any) {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateInitialViewController()
        self.present(vc!, animated: false, completion: nil)
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
    
    //MARK: - FB Login
    @IBAction func loginActionFB() {
        FBSDKLoginManager().logIn(withReadPermissions: ["email", "public_profile"], from: self, handler:
            { (result, err) in
                if err != nil {
                    print("FB Login Failed: ", err ?? "Error is nil")
                } else {
                    if (result?.isCancelled)! {
                        return
                    } else {
                        if let userId = result?.token.userID {
                            UserDefaults.standard.setValue(userId, forKey: "fbToken")
                            FBSDKGraphRequest(graphPath: "me", parameters: ["fields": "name, email"]).start(completionHandler: { (connection, result, error) -> Void in
                                if (error == nil) {
                                    if let res = result as? [String:Any] {
                                        UserDefaults.standard.setValue(res["name"], forKey: "fbName")
                                        UserDefaults.standard.setValue(res["email"], forKey: "fbEmail")
                                    }
                                }
                            })
                        }
                        self.signInFirebaseWithFB()
                    }
                }
        })
    }
    
    func signInFirebaseWithFB() {
        let accessToken = FBSDKAccessToken.current()
        guard let accessTokenString = accessToken?.tokenString else {return}
        let credentials = FIRFacebookAuthProvider.credential(withAccessToken: accessTokenString)
        FIRAuth.auth()?.signIn(with: credentials, completion: { (user, error) in
            if error != nil {
                print("FB Firebase Login Failed: \(error)")
                return
            }
            print("Accessed FB Firebase with user: \(user)")
            //do something
            self.loginAction(self)
        })
    }
    
    func loginButtonDidLogOut(_ loginButton: FBSDKLoginButton!) {
        print("Logged out of Facebook")
    }
    
    func loginButton(_ loginButton: FBSDKLoginButton!, didCompleteWith result: FBSDKLoginManagerLoginResult!, error: Error!) {
        if error != nil {
            print(error)
        } else {
            print("Logged in to Facebook")
        }
    }
    
    

}
