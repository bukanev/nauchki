import React from "react";

export const Gallery = () => {
  return (
    <div className="gallery">
      <div className="gallery__title">Галерея (фотоальбом)</div>
      <div className="gallery__photos">
        <div
          className="gallery__photo"
          style={{
            backgroundImage:
              "url(https://storage.yandexcloud.net/nau4ki/7fa984a3-4e99-4f62-8d5a-5a7d9f6ecf9f)",
          }}
        ></div>

        <div className="gallery__photo"></div>
      </div>
    </div>
  );
};
