const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

// URL: "https://us-central1-quiero-mas.cloudfunctions.net/enviarLista/"
// parámetros: ["user": String, "list": [String]]  ---  ej: ["user": "userEmail@gmail.com", "list": ["2 huevos", "4 bananas"]]
// respuesta: ["success": Bool]
exports.enviarLista = functions.https.onRequest((req, res) => {
	var dic = req.body;
	var userEmail = dic.email;
	var list = dic.list;
	var parsedList = list.map(function (ingredient) {
		return "- " + ingredient + "\n";
	});
	var emailText = "Tu lista de compras de Quiero Más!\n" + parsedList.join("");

	console.log('enviando mail a: ', userEmail);
	console.log('cuerpo de mail: ', emailText);

	var send = require('gmail-send')({
	  user: 'ferfrassia@gmail.com',
	  pass: 'kqnxqwpbyhqpscsq',
	  to:   userEmail,
	  subject: 'Lista de compras de Quiero Más!',
	  text:    emailText,
	});
	 
	send({}, function (err, res) {
		if (err != null) {
			console.log('error: ', err);
			console.log('result: ', res);
		}
	});

	res.send({success: true});
});


exports.registrar = functions.https.onRequest((req, res) => {
	const original = req.query.text;
	console.log('original: ', original)
	var params = original.split(' ')
	console.log('params: ', params)
	if (params.length >= 7) {
		var firebaseID = params[0]
		var nombre = params[1]
		var nacimiento = params[2]
		var mail = params[3]
		var fechaBebe = params[4]
	 	var nombreBebe = params[5]
		var apodoBebe = params[6]
		
		var nombreSpace = nombre.replace("-", " ");
		var nombreBebeSpace = nombreBebe.replace("-", " ");
		var apodoBebeSpace = apodoBebe.replace("-", " ");
		console.log('registrando')
		console.log('firebaseID: ', firebaseID)
		console.log('nombre: ', nombreSpace)
		console.log('nacimiento: ', nacimiento)
		console.log('mail: ', mail)
		console.log('fechaBebe: ', fechaBebe)
		console.log('nombreBebe: ', nombreBebeSpace)
		console.log('apodoBebe', apodoBebeSpace)

		//Datos
		if (nombreSpace != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Datos').child('Nombre Completo').set(nombreSpace);
		}
		
		if (mail != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Datos').child('Email').set(mail);
		}
		
		if (nacimiento != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Datos').child('Fecha de Nacimiento').set(nacimiento);
		}
		

		//Bebe
		if (nombreBebeSpace != '' && apodoBebeSpace != '') {
			admin.database().ref('/Usuarios').child(firebaseID).child('Bebé').child('Apodo').set(apodoBebeSpace);
			admin.database().ref('/Usuarios').child(firebaseID).child('Bebé').child('Nombre').set(nombreBebeSpace);
			admin.database().ref('/Usuarios').child(firebaseID).child('Bebé').child('Fecha de Nacimiento').set(fechaBebe);

			return admin.database().ref('Bebés de menos de 6 meses').once('value').then(snap => {
		    if (snap.exists()) {
		    	if (snap.child(firebaseID).exists()) {
		    		var refString = '/Bebés de menos de 6 meses/' + firebaseID;
		    		var adaRef = admin.database().ref(refString);
					adaRef.remove()
					  .then(function() {
					    console.log("Remove succeeded.")
					  })
					  .catch(function(error) {
					    console.log("Remove failed: " + error.message)
					  });
		    	}
		    }
		    res.status(200).send('Updated Successfully');
		  });
		} else {
			if (fechaBebe != '') {
				admin.database().ref('/Bebés de menos de 6 meses').child(firebaseID).set(fechaBebe);
			}
		}
		res.json({success: true});
	}
	res.json({success: false});
});
