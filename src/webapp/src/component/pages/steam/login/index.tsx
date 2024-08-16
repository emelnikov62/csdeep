import React from "react";

class SteamLogin extends React.Component {

    render() {
       return (
            <div>
               <form id="form" action="https://steamcommunity.com/openid/login" method="post" >
                  <input  name="openid.identity" value="http://specs.openid.net/auth/2.0/identifier_select"/>
                  <input  name="openid.claimed_id" value="http://specs.openid.net/auth/2.0/identifier_select"/>
                  <input  name="openid.ns" value="http://specs.openid.net/auth/2.0"/>
                  <input  name="openid.mode" value="checkid_setup"/>
                  <input  name="openid.realm" value="http://localhost:8080/#/"/>
                  <input  name="openid.return_to" value="http://localhost:8080/#/steam/login/redirect"/>
                  <input type="image" name="submit"
                        src="https://steamcdn-a.akamaihd.net/steamcommunity/public/images/steamworks_docs/brazilian/sits_small.png"
                        alt="Submit"/>
               </form>
         </div>
       );
    }

}

export default SteamLogin;