

Ext.onReady(function(){
Ext.QuickTips.init();

    var loginHandle = function(){
                if(fp.getForm().isValid()){
                    var sb = Ext.getCmp('form-statusbar');
                    sb.showBusy('Checking Authentication...');
                    fp.getEl().mask();
                    fp.getForm().submit({
                        url: 'php_cls_fl/login.php',
                        success: function(form,action){
                            try{
                               //Ext.MessageBox.alert("Message","Try" +action.response.responseText);
                               sb.setStatus({
                                   text:'Login Successfull',
                                   iconCls:'x-status-valid',
                                   clear: true
                                });
                                //fp.getEl().unmask();
                                window.location = "profile.php";// where they are redirected to when login

                            }catch(err){
                               Ext.MessageBox.alert("Message","err"+action.response.responseText);
                            }


                        },
                        failure: function(form,action){
                            try{
                               //Ext.MessageBox.alert("Message","tr"+action.response.responseText);
                               sb.setStatus({
                                   text: 'Login Error!!!',
                                   iconCls: 'x-status-error',
                                   clear: false
                                })
                                fp.getEl().unmask();
                            }catch(err){
                               Ext.MessageBox.alert("Message","er"+action.response.responseText);
                            }

                        }
                    });
                }
            }
    var fp = new Ext.FormPanel({
        id: 'status-form',
        renderTo: Ext.getBody(),
        labelWidth: 60,
        width: 350,
        buttonAlign: 'right',
        border: false,
        bodyStyle: 'padding:10px 10px 0;',
        defaults: {
            anchor: '92%',
            allowBlank: false,
            selectOnFocus: true,
            msgTarget: 'side'
        },
        items:[{
            xtype: 'textfield',
            fieldLabel: 'Email',
            blankText: 'Email address is required',
            name: "loginUsernameNm"
        },{
            xtype: 'textfield',
            inputType: 'password',
            fieldLabel: 'Password',
            blankText: 'Password is required',
            name: "loginPasswordNm"
        }],
        keys:[{
                key: Ext.EventObject.ENTER,
                fn: loginHandle,
                scope: this
        }],
        buttons: [{
            text: 'Login',
            handler: loginHandle
        },{
            text: 'Clear',
            handler: function(){
                fp.getForm().reset();
            }
        }]
    });

    new Ext.Panel({
        renderTo: "login",
        width: 210,
        height: 90,
        autoHeight: true,
        layout: 'fit',
        items: fp,
        bbar: new Ext.ux.StatusBar({
            id: 'form-statusbar',
            defaultText: 'enter email and password to login',
            plugins: new Ext.ux.ValidationStatus({form:'status-form'})
        })
    });

});