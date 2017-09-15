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
    
    //Login View
    @IBOutlet weak var loginView: UIView!
    
    //Email View
    @IBOutlet weak var emailView: UIView!
    @IBOutlet weak var loginEmailTF: UITextField!
    @IBOutlet weak var loginPassTF: UITextField!
    
    //Forgot View
    @IBOutlet weak var forgotView: UIView!
    @IBOutlet weak var forgotTF: UITextField!
    
    //Register View
    @IBOutlet weak var registerView: UIView!
    @IBOutlet weak var registerNameTF: UITextField!
    @IBOutlet weak var registerDateTF: UITextField!
    @IBOutlet weak var registerEmailTF: UITextField!
    @IBOutlet weak var registerPassTF: UITextField!
    @IBOutlet weak var registerConfirmPassTF: UITextField!
    
    //Nacio View
    @IBOutlet weak var nacioOpacityView: UIView!
    @IBOutlet weak var nacioView: UIView!
    @IBOutlet weak var partoView: UIView!
    @IBOutlet weak var noView: UIView!
    @IBOutlet weak var noButton: UIButton!
    @IBOutlet weak var deliveryDateTF: UITextField!
    @IBOutlet weak var notificationPopUp: UIView!
    
    //Other
    @IBOutlet weak var loginScroll: UIScrollView!
    @IBOutlet weak var dasanTopConstraint: NSLayoutConstraint!
    @IBOutlet weak var emailNoAccountConstraint: NSLayoutConstraint!
    @IBOutlet weak var forgotPassNoAccountConstraint: NSLayoutConstraint!
    @IBOutlet weak var quieroMasLogoConstraint: NSLayoutConstraint!
    @IBOutlet weak var datePicker: UIDatePicker!
    @IBOutlet weak var spinner: UIActivityIndicatorView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setAVPlayer()
        adjustDasanConstraint()
        adjustEmailNoAccountConstraint()
        adjustForgotPasswordNoAccountConstraint()
        setTapGestures()
        setDatePickerInputs()
        setObservers()
        if DeviceType.IS_IPHONE_4_OR_LESS {
            quieroMasLogoConstraint.constant = 8
        }
    }
    
    func setAVPlayer() {
        let theUrl = NSURL(fileURLWithPath: Bundle.main.path(forResource: "video-background_1200x720", ofType: "mp4")!)
        
        avPlayer = AVPlayer(url: theUrl as URL)
        avPlayerLayer = AVPlayerLayer(player: avPlayer)
        avPlayerLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
        avPlayer.actionAtItemEnd = .none
        avPlayerLayer.frame = view.layer.frame
        
        view.layer.insertSublayer(avPlayerLayer, at: 0)
        NotificationCenter.default.addObserver(self, selector: #selector(playerItemDidReachEnd(notification:)), name: NSNotification.Name.AVPlayerItemDidPlayToEndTime, object: avPlayer.currentItem)
    }
    
    func setObservers() {
        NotificationCenter.default.addObserver(self, selector: #selector(self.passUpdated), name: NSNotification.Name(rawValue: perfilPassUpdated), object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.connectionAlert), name: NSNotification.Name(rawValue: connectionError), object: nil)
    }
    
    func setTapGestures() {
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(self.tapGestureHandler))
        self.view.addGestureRecognizer(tapGesture)
    }
    
    //Set up
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
        let offSet = CGPoint(x:0, y:0)
        loginScroll.setContentOffset(offSet, animated: true)
        self.view.endEditing(true)
    }
    
    func setDatePickerInputs() {
        registerDateTF.inputView = datePicker
        deliveryDateTF.inputView = datePicker
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
        
        if DeviceType.IS_IPHONE_4_OR_LESS {
            dasanTopConstraint.constant = 22
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
        
        if DeviceType.IS_IPHONE_4_OR_LESS {
            emailNoAccountConstraint.constant = 0
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
        
        if DeviceType.IS_IPHONE_4_OR_LESS {
            forgotPassNoAccountConstraint.constant = 20
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
        var val:CGFloat = 0
        if DeviceType.IS_IPHONE_6P {
            val = 200
        } else if DeviceType.IS_IPHONE_6 {
            val = 260
        } else {
            val = 360
        }
        let offSet = CGPoint(x: 0, y: min(view.frame.origin.y, val))
        loginScroll.setContentOffset(offSet, animated: true)
    }
    
    func scrollToTop(animated: Bool) {
        let offSet = CGPoint(x: 0, y: 0)
        loginScroll.setContentOffset(offSet, animated: animated)
    }
    
    //MARK: - Show View
    func showLoginView() {
        self.view.endEditing(true)
        scrollToTop(animated: false)
        loginView.isHidden = false
        emailView.isHidden = true
        forgotView.isHidden = true
        registerView.isHidden = true
        nacioView.isHidden = true
        loginScroll.isScrollEnabled = false
    }
    
    func showEmailView() {
        self.view.endEditing(true)
        scrollToTop(animated: false)
        loginView.isHidden = true
        emailView.isHidden = false
        forgotView.isHidden = true
        registerView.isHidden = true
        nacioView.isHidden = true
        loginScroll.isScrollEnabled = false
    }
    
    func showForgotView() {
        self.view.endEditing(true)
        scrollToTop(animated: false)
        loginView.isHidden = true
        emailView.isHidden = true
        forgotView.isHidden = false
        registerView.isHidden = true
        nacioView.isHidden = true
        loginScroll.isScrollEnabled = false
    }
    
    func showRegisterView() {
        self.view.endEditing(true)
        scrollToTop(animated: false)
        loginView.isHidden = true
        emailView.isHidden = true
        forgotView.isHidden = true
        registerView.isHidden = false
        nacioView.isHidden = true
        loginScroll.isScrollEnabled = true
    }
    
    func showNacioView() {
        self.view.endEditing(true)
        scrollToTop(animated: false)
        loginView.isHidden = true
        emailView.isHidden = true
        forgotView.isHidden = true
        registerView.isHidden = true
        nacioView.isHidden = false
        loginScroll.isScrollEnabled = false
    }
    
    func restartLogin() {
        noView.backgroundColor = .clear
        noButton.setTitleColor(.white, for: .normal)
        partoView.isHidden = true
        hideNotificationPopUp()
        clearAllTextFields()
        showLoginView()
    }
    
    func clearAllTextFields() {
        loginEmailTF.text = ""
        loginPassTF.text = ""
        registerNameTF.text = ""
        registerDateTF.text = ""
        registerEmailTF.text = ""
        registerPassTF.text = ""
        registerConfirmPassTF.text = ""
        deliveryDateTF.text = ""
    }
    
    
    //MARK: - IBAction Login
    
    //MARK: - IBAction Email
    @IBAction func registerAction(_ sender: Any) {
        showRegisterView()
    }
    
    @IBAction func logInWithEmail(_ sender:Any) {
        if loginEmailTF.text == nil || loginEmailTF.text! == "" || loginPassTF.text == nil || loginPassTF.text! == "" {
            let alert = UIAlertController(title: "",
                                          message: "Faltan llenar campos",
                                          preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK",
                                             style: .cancel, handler: { (alert: UIAlertAction) in
            })
            
            alert.addAction(cancelAction)
            self.present(alert, animated: true, completion: nil)
            return
        } else {
            spinner.startAnimating()
            FIRAuth.auth()?.signIn(withEmail: loginEmailTF.text!, password: loginPassTF.text!, completion: { (user, error) in
                if error != nil {
                    let alert = UIAlertController(title: "",
                                                  message: "No se encontró la cuenta, pruebe nuevamente",
                                                  preferredStyle: UIAlertControllerStyle.alert)
                    
                    let cancelAction = UIAlertAction(title: "OK",
                                                     style: .cancel, handler: { (alert: UIAlertAction) in
                    })
                    
                    alert.addAction(cancelAction)
                    self.present(alert, animated: true, completion: nil)
                } else {
                    FirebaseAPI.getUser(firebaseID: (user?.uid)!)
                    self.showMainVC()
                }
                self.spinner.stopAnimating()
            })
        }
    }
    
    //MARK: - IBAction Forgot
    @IBAction func forgotPass(_ sender: Any) {
        if let email = forgotTF.text {
            guard email != "" else {return}
            FirebaseAPI.changePasswordWithEmail(email: email)
        }
    }
    
    
    //MARK: - IBAction Register
    @IBAction func createAccount(_ sender: Any) {
        if registerNameTF.text == "" || registerDateTF.text == "" || registerEmailTF.text == "" || registerPassTF.text == "" || registerConfirmPassTF.text == "" {
            let alert = UIAlertController(title: "",
                                          message: "Faltan completar datos",
                                          preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK",
                                             style: .cancel, handler: nil)
            
            alert.addAction(cancelAction)
            present(alert, animated: true, completion: nil)
            return
        }
        
        if registerPassTF.text != registerConfirmPassTF.text {
            let alert = UIAlertController(title: "",
                                          message: "Las contraseñas no coinciden",
                                          preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK",
                                             style: .cancel, handler: nil)
            
            alert.addAction(cancelAction)
            present(alert, animated: true, completion: nil)
            return
        }
        
        if (registerPassTF.text?.characters.count)! < 6 || (registerConfirmPassTF.text?.characters.count)! < 6 {
            let alert = UIAlertController(title: "",
                                          message: "Las contraseñas deben ser de 6 caracteres o más",
                                          preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK",
                                             style: .cancel, handler: nil)
            
            alert.addAction(cancelAction)
            present(alert, animated: true, completion: nil)
            return
        }
        
        showNacioView()
    }
    
    @IBAction func goToLoginView(_ sender: Any) {
        showLoginView()
    }
    
    //MARK: - IBAction Nacio
    @IBAction func bornYesAction(_ sender: Any) {
        performSegue(withIdentifier: "babyDataSegue", sender: self)
        restartLogin()
    }
    
    @IBAction func bornNoAction(_ sender: Any) {
        noView.backgroundColor = .white
        noButton.setTitleColor(.orange, for: .normal)
        partoView.isHidden = false
    }
    
    @IBAction func nacioContinueAction(_ sender: Any) {
        if deliveryDateTF.text == "" {
            let alert = UIAlertController(title: "",
                                          message: "Completar fecha",
                                          preferredStyle: UIAlertControllerStyle.alert)
            
            let cancelAction = UIAlertAction(title: "OK",
                                             style: .cancel, handler: nil)
            
            alert.addAction(cancelAction)
            present(alert, animated: true, completion: nil)
        } else {
            showNotificationPopUp()
        }
    }
    
    func showNotificationPopUp() {
        view.endEditing(true)
        notificationPopUp.isHidden = false
        nacioOpacityView.isHidden = false
        
    }
    
    func hideNotificationPopUp() {
        notificationPopUp.isHidden = true
        nacioOpacityView.isHidden = true
    }
    
    @IBAction func showEmailAction(_ sender: Any) {
        showEmailView()
    }
    
    @IBAction func forgotPasswordAction(_ sender: Any) {
        showForgotView()
    }
    
    @IBAction func loginAction(_ sender: Any) {
        spinner.startAnimating()
        FIRAuth.auth()?.createUser(withEmail: registerEmailTF.text!, password: registerPassTF.text!, completion: { (user, error) in
            if error != nil {
                let alert = UIAlertController(title: "",
                                              message: "No se pudo crear la cuenta, puede que ya exista una cuenta con este email",
                                              preferredStyle: UIAlertControllerStyle.alert)
                
                let cancelAction = UIAlertAction(title: "OK",
                                                 style: .cancel, handler: { (alert: UIAlertAction) in
                                                    self.restartLogin()
                })
                
                alert.addAction(cancelAction)
                self.present(alert, animated: true, completion: nil)
                self.spinner.stopAnimating()
                return
            } else {
                    self.storeUserDataInUserDefaults()
                    FirebaseAPI.storeFirebaseUser(firebaseID: (user?.uid)!,
                                                  name: self.registerNameTF.text!,
                                                  birthday: self.registerDateTF.text!,
                                                  email: self.registerEmailTF.text!,
                                                  babyName: nil,
                                                  babyNickName: nil,
                                                  babyBirthday: self.deliveryDateTF.text!)
                    self.showMainVC()
            }
        })
    }
    
    func storeUserDataInUserDefaults() {
        var userDic = [String:[String:String]]()
        userDic["Datos"] = [String:String]()
        
        if registerNameTF.text != nil && registerNameTF.text! != "" {
            userDic["Datos"]?["Nombre Completo"] = registerNameTF.text!
        }
        
        if registerDateTF.text != nil && registerDateTF.text! != "" {
            userDic["Datos"]?["Fecha de Nacimiento"] = registerDateTF.text!
        }
        
        if registerEmailTF.text != nil && registerEmailTF.text! != "" {
            userDic["Datos"]?["Email"] = registerEmailTF.text!
        }
        
        if deliveryDateTF.text != nil && deliveryDateTF.text! != "" {
            userDic["Bebé"] = ["Fecha de Nacimiento": deliveryDateTF.text!]
        }
        
        UserDefaults.standard.set(userDic, forKey: defPerfil)
    }
    
    @IBAction func datePickerValueChanged(_ sender: Any) {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "dd/MM/yyyy"
        let selectedDate = dateFormatter.string(from: datePicker.date)
        
        if registerDateTF.isFirstResponder {
            registerDateTF.text = selectedDate
        }
        
        if deliveryDateTF.isFirstResponder {
            deliveryDateTF.text = selectedDate
        }
    }
    
    //MARK: - FB Login
    @IBAction func loginActionFB() {
        spinner.startAnimating()
        FBSDKLoginManager().logIn(withReadPermissions: ["email", "public_profile"], from: self, handler:
            { (result, err) in
                if err != nil {
                    print("FB Login Failed: ", err ?? "Error is nil")
                    self.spinner.stopAnimating()
                } else {
                    if (result?.isCancelled)! {
                        self.spinner.stopAnimating()
                        return
                    } else {
                        if (result?.token.userID) != nil {
                            FBSDKGraphRequest(graphPath: "me", parameters: ["fields": "name, email"]).start(completionHandler: { (connection, result, error) -> Void in
                                if (error == nil) {
                                    if let res = result as? [String:Any] {
                                        let datosDic = ["Nombre Completo":res["name"] as? String, "Email":res["email"] as? String]
                                        let userDic = ["Datos" : datosDic]
                                        UserDefaults.standard.set(userDic, forKey: defPerfil)
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
                print("FB Firebase Login Failed: \(String(describing: error))")
                self.spinner.stopAnimating()
                return
            }
            print("Accessed FB Firebase with user: \(String(describing: user))")
            FirebaseAPI.getDatosPerfil()
            self.showMainVC()
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
    
    //MARK: - Show VC
    func showMainVC() {
        let story = UIStoryboard(name: "Main", bundle: nil)
        let vc = story.instantiateInitialViewController()
        self.present(vc!, animated: true, completion: nil)
    }
    
    //MARK: - Segue
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "babyDataSegue" {
            let vc = segue.destination as? BabyDataViewController
            vc?.name = registerNameTF.text
            vc?.email = registerEmailTF.text
            vc?.password = registerPassTF.text
            vc?.birthday = registerDateTF.text
        }
    }
    
    //MARK: - Aux
    func passUpdated() {
        if let email = forgotTF.text {
            showAlert(text: "Email de cambio de contraseña enviado exitosamente a \(email)")
        }
    }
    
    func connectionAlert() {
        showAlert(text: "Error de conexión")
    }
    
    func showAlert(text: String) {
        let alert = UIAlertController(title: "",
                                      message: text,
                                      preferredStyle: UIAlertControllerStyle.alert)
        
        let cancelAction = UIAlertAction(title: "OK",
                                         style: .cancel, handler: nil)
        
        alert.addAction(cancelAction)
        present(alert, animated: true, completion: nil)
        return
    }
    

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
