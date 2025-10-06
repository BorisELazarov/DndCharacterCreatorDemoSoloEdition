import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { Proficiency } from '../../../shared/interfaces/proficiency';
import { Sort } from '../../../core/sort';
import { Filter } from '../common/filter';

@Injectable({
  providedIn: 'root'
})
export class ProficiencyService {
  url: string='http://localhost:8080/api/proficiencies';
  constructor(private httpClient:HttpClient) {}
  public getAllDeleted(sort:Sort,filter:Filter): Observable<HttpResponse<Proficiency[]>>{
    return this.httpClient
      .get<Proficiency[]>(
        this.url+'/deleted'+'?name='+filter.name+'&type='+filter.type
        +'&sortBy='+sort.sortBy+'&ascending='+sort.ascending,
        {observe:'response'}
      );
  }
  public getAll(sort:Sort,filter:Filter): Observable<HttpResponse<Proficiency[]>>{
    return this.httpClient
      .get<Proficiency[]>(
        this.url+'?name='+filter.name+'&type='+filter.type
        +'&sortBy='+sort.sortBy+'&ascending='+sort.ascending,
        {observe:'response'}
      );
  }
  public getById(id:number): Observable<HttpResponse<Proficiency>>{
    return this.httpClient
      .get<Proficiency>(this.url+'/'+id,{observe:'response'});
  }
  public create(proficiency: Proficiency):void{
    this.httpClient.post<Proficiency>(this.url, proficiency).subscribe();
  }
  public edit(proficiency: Proficiency):void{
    this.httpClient.put<Proficiency>(this.url, proficiency).subscribe();
  }
  public delete(id:number):void{
    this.httpClient.delete(this.url+"?id="+id).subscribe();
  }
  public confirmedDelete(id:number):void{
    this.httpClient.delete(this.url+"/confirmedDelete?id="+id).subscribe();
  }
  public restore(id:number):void{
    this.httpClient.put(this.url+"/restore/"+id,null).subscribe();
  }
}
