#include "runtime/src/audio_gain.h"
#include <cassert>
#include <iostream>
using namespace nx::plat;
int main() {
    assert(parse_audio_gain_percent(nullptr)==150);
    assert(parse_audio_gain_percent("invalid")==150);
    assert(parse_audio_gain_percent("999")==200);
    assert(parse_audio_gain_percent("-9")==50);
    int16_t unity[]={-32768,32767,-1000,1000,0,0};
    apply_audio_gain(unity,3,100);
    assert(unity[0]==-32768 && unity[1]==32767 && unity[2]==-1000 && unity[3]==1000 && unity[4]==0);
    int16_t quiet[]={1000,-2000,0,0};
    apply_audio_gain(quiet,2,150);
    assert(quiet[0]==1500 && quiet[1]==-3000 && quiet[2]==0 && quiet[3]==0);
    int16_t peak[]={32767,-16384,-32768,32767};
    apply_audio_gain(peak,2,200);
    assert(peak[0]>26000 && peak[0]<32767 && peak[1]<-13000 && peak[1]>-17000);
    assert(peak[2]>-32768 && peak[2]<-26000 && peak[3]>26000 && peak[3]<32767);
    int16_t half[]={-32768,32767};
    apply_audio_gain(half,1,50);
    assert(half[0]==-16384 && half[1]==16384);
    std::cout << "Audio gain tests passed: default, bounds, unity, boost, silence, stereo limiting and attenuation\n";
}
