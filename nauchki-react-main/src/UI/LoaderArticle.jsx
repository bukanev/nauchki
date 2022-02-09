import React from "react"
import ContentLoader from "react-content-loader"

export const LoaderArticle = () => {
    return (
        <ContentLoader 
        speed={5}
        width={310}
        height={300}
        viewBox="0 0 310 300"
        backgroundColor="#c5c5c5"
        foregroundColor="#ecebeb"
      >
        <rect x="0" y="0" rx="20" ry="20" width="310" height="208" /> 
        <rect x="52" y="142" rx="0" ry="0" width="152" height="18" /> 
        <rect x="0" y="225" rx="11" ry="11" width="310" height="30" />
      </ContentLoader>
    )
}
