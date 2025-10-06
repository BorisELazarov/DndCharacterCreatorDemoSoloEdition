import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Spell } from '../../../shared/interfaces/spell';
import { Observable } from 'rxjs';
import { Sort } from '../../../core/sort';
import { Filter } from '../../../features/spells/filter';

@Injectable({
  providedIn: 'root'
})
export class SpellService {
  readonly url: string = 'http://localhost:8080/api/spells';
  constructor(private httpClient:HttpClient) {
  }
  public getAllDeleted(sort:Sort,filter:Filter): Observable<HttpResponse<Spell[]>>{
    return this.httpClient
      .post<Spell[]>(
        this.url+'/getAll/deleted',{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }
  public getAll(sort:Sort,filter:Filter): Observable<HttpResponse<Spell[]>>{
    return this.httpClient
      .post<Spell[]>(
        this.url+'/getAll',{
          filter:filter,
          sort:sort
        },
        {observe:'response'}
      );
  }
  public getAllUnfiltered(): Observable<HttpResponse<Spell[]>>{
    return this.httpClient
      .get<Spell[]>(
        this.url,
        {observe:'response'}
      );
  }
  public getById(id:number): Observable<HttpResponse<Spell>>{
    return this.httpClient
      .get<Spell>(this.url+'/'+id,{observe:'response'});
  }
  public create(spell: Spell):Observable<Spell>{
    return this.httpClient.post<Spell>(this.url, spell);
  }
  public edit(spell: Spell):Observable<Spell>{
    return this.httpClient.put<Spell>(this.url, spell);
  }
  public delete(id:number):Observable<Spell>{
    return this.httpClient.delete<Spell>(this.url+"?id="+id);
  }
  public confirmedDelete(id:number):Observable<Spell>{
    return this.httpClient.delete<Spell>(this.url+"/confirmedDelete?id="+id);
  }
  public restore(id:number):Observable<Object>{
    return this.httpClient.put<Spell>(this.url+"/restore/"+id,null);
  }
}
