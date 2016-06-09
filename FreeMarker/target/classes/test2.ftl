<#if OGA_COD == "HLM">
http://serveur1.macompagnie.com/gestionDesCourriers/imprimerFichePatrimoine?codeImmeuble=${COD_PAT1}-${COD_PAT2}
<#else>
http://serveur2.macompagnie.com/intranet/afficherPagePatrimoine?id=${COD_PAT2}
</#if>