import React from "react";
import { useLocation } from 'react-router-dom'

const SteamRedirect = () => {
   const search = useLocation().search
   const searchParams = new URLSearchParams(search)
   const SteamID = searchParams.get('openid.claimed_id')?.split('/').pop()
   

   for (let element of searchParams.keys()) {
      console.log(element);
      console.log(searchParams.get(element));
  }

  console.log(SteamID)

   return (
       <div>SteamID: {SteamID}</div>
   )
}

export default SteamRedirect;