SELECT 
  umafores.[IdConglome] AS 'Conglomerado'
  ,umafores.[CVE_ENT] AS 'IdEstado'
  ,umafores.[CVE_UMAF_E] as 'Clave_UMAFOR'
  /*    ,especies_por_cgl.[Estado]*/      
  ,especies_por_cgl.[Familia_APG] AS 'Familia_APG'
  ,especies_por_cgl.[Genero_APG] AS 'Genero_APG'
  ,especies_por_cgl.[Especie_APG] AS 'Especie_APG'
  ,especies_por_cgl.[Categoria_Infra_APG] AS 'Categoria_Infra_APG'
  ,especies_por_cgl.[Infra_APG] AS 'Infra_APG'
  ,especies_por_cgl.[NombreCientifico_APG] AS 'NombreCientifico_APG'
  ,umafores.[CVEECON1] as 'Eco_N1'
  ,umafores.[CVEECON2] as 'Eco_N2'
  ,umafores.[CVEECON3] as 'Eco_N3'
  ,umafores.[CVEECON4] as 'Eco_N4'
FROM [UMAFORES_CGL_2017].[dbo].[UMAFORES_UPMS] umafores 
JOIN [UMAFORES_CGL_2017].[dbo].[Especies_por_cgl] especies_por_cgl ON especies_por_cgl.[Conglomerado]= umafores.[IdConglome]
