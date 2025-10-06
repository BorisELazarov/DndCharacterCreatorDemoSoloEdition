import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { Proficiency } from '../../interfaces/proficiency';
import { Sort } from '../../../core/sort';
import { ProficiencyFilter } from '../../filters/proficiency-filter';

@Injectable({
  providedIn: 'root'
})
export class ProficiencyService {
  readonly url: string='http://localhost:8080/api/proficiencies';
  constructor(private httpClient:HttpClient) {}
  public getAllDeleted(sort:Sort,filter:ProficiencyFilter): Observable<HttpResponse<Proficiency[]>>{
    return this.httpClient
      .post<Proficiency[]>(
        this.url+'/getAll/deleted',{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }
  public getAllUnfiltered(): Observable<HttpResponse<Proficiency[]>>{
    return this.httpClient.get<Proficiency[]>(this.url,{observe:'response'});
  }
  public getAll(sort:Sort,filter:ProficiencyFilter): Observable<HttpResponse<Proficiency[]>>{
    return this.httpClient
      .post<Proficiency[]>(
        this.url+'/getAll',{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }
  public getById(id:number): Observable<HttpResponse<Proficiency>>{
    return this.httpClient
      .get<Proficiency>(this.url+'/'+id,{observe:'response'});
  }
  public create(proficiency: Proficiency):Observable<Proficiency>{
    return this.httpClient.post<Proficiency>(this.url, proficiency);
  }
  public edit(proficiency: Proficiency):Observable<Proficiency>{
    return this.httpClient.put<Proficiency>(this.url, proficiency);
  }
  public delete(id:number):Observable<Proficiency>{
    return this.httpClient.delete<Proficiency>(this.url+"?id="+id);
  }
  public confirmedDelete(id:number):Observable<Proficiency>{
    return this.httpClient.delete<Proficiency>(this.url+"/confirmedDelete?id="+id);
  }
  public restore(id:number):Observable<Proficiency>{
    return this.httpClient.put<Proficiency>(this.url+"/restore/"+id,null);
  }
}
