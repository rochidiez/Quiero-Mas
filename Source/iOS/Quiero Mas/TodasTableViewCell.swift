//
//  TodasTableViewCell.swift
//  Quiero Mas
//
//  Created by Fernando N. Frassia on 5/29/17.
//  Copyright © 2017 Fernando N. Frassia. All rights reserved.
//

import UIKit

class TodasTableViewCell: UITableViewCell {
    
    @IBOutlet weak var recetaName: UILabel!
    @IBOutlet weak var recetaImg: UIImageView!
    @IBOutlet weak var recetaFav: UIButton!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
