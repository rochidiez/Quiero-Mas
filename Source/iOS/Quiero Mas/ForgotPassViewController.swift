//
//  ForgotPassViewController.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 4/24/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit
import AVFoundation

class ForgotPassViewController: UIViewController {

    var avPlayer: AVPlayer!
    var avPlayerLayer: AVPlayerLayer!
    var paused: Bool = false
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let theURL = NSURL(fileURLWithPath: "/Users/fer/Documents/Workspace/iOS/Quiero Mas/Quiero Mas/video-background_1200x720.mp4")
        
        avPlayer = AVPlayer(url: theURL as URL)
        
        avPlayerLayer = AVPlayerLayer(player: avPlayer)
        avPlayerLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
        avPlayer.actionAtItemEnd = .none
        
        avPlayerLayer.frame = view.layer.frame
        
        view.layer.insertSublayer(avPlayerLayer, at: 0)
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(playerItemDidReachEnd(notification:)),
                                               name: NSNotification.Name.AVPlayerItemDidPlayToEndTime,
                                               object: avPlayer.currentItem)
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
    
    //MARK: - IBAction
    @IBAction func emailAction(_ sender: Any) {
        performSegue(withIdentifier: "EmailSegue", sender: self)
    }

}
