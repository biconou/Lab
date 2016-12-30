SELECT totie.totie_cod codeTiers,
       totie.totie_qual qualiteTiers,
       totie.totie_lib libelleTiers,
       mgadr.mgadr_numvoi adresseNumeroVoie,
       mgadr.mgtvo_cod adresseTypeVoie,
       mgadr.mgadr_nomvoi adresseNomVoie,
       mgadr.mgadr_codpos adresseCodePostal,
       mgadr.mgadr_libcom adresseLibelleCommune,
       'SIGNATAIRE' role
FROM totie,
     totad,
     mgadr
WHERE totie.totie_cod IN (SELECT totie_cod
                    FROM glcsi
                    WHERE (glcon_num,glcon_numver) IN (SELECT glcon_num,
                                                              glcon_numver
                                                       FROM glcon
                                                       WHERE glnac_cod IN (SELECT glnac_cod
                                                                           FROM glnac
                                                                           WHERE glnac_valint != 'SERVICE'
                                                                           AND   glnac_valint != 'PRISABAIL'
                                                                           OR    glnac_valint IS NULL)))
AND   totad.mgadr_num = mgadr.mgadr_num
AND   totad.totad_dtf IS NULL
AND   totad.totie_cod = totie.totie_cod