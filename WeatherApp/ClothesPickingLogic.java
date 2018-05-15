public class ClothesPickingLogic {
    public static Clothes[] whatClothes(float temp,Weather weather ){
        Clothes items[] = {Clothes.TSHIRT, Clothes.TROUSERS,Clothes.TRAINERS}; //0 is top, 1 is bottom, 2 is shoes
        //top logic
        if(weather==Weather.SNOWY | temp<5.0){
            items[0]=Clothes.WARMCOAT;
        }else if(weather==Weather.RAINY | weather== Weather.THUNDER){
            items[0]=Clothes.RAINCOAT;
        }else if(temp<17){
            items[0]=Clothes.JACKET;
        }
        //bottom logic
        if(weather!=Weather.RAINY & weather!=Weather.SNOWY & weather!= Weather.THUNDER & temp>17){
            items[1]=Clothes.SHORTS;
        }

        //shoes logic
        if(weather== Weather.RAINY | weather==Weather.THUNDER|weather==Weather.SNOWY){
            items[2]=Clothes.RAINBOOTS;
        }else if(temp>25.0 & weather==Weather.SUNNY){
            items[2]=Clothes.FLIPFLOP;
        }

        return items;
    }
}
